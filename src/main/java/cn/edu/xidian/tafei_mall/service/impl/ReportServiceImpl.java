package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.OrderItemMapper;
import cn.edu.xidian.tafei_mall.mapper.OrderMapper;
import cn.edu.xidian.tafei_mall.mapper.ProductMapper;
import cn.edu.xidian.tafei_mall.mapper.ReviewMapper;
import cn.edu.xidian.tafei_mall.model.entity.Order;
import cn.edu.xidian.tafei_mall.model.entity.OrderItem;
import cn.edu.xidian.tafei_mall.model.entity.Product;
import cn.edu.xidian.tafei_mall.model.entity.Review;
import cn.edu.xidian.tafei_mall.model.vo.Response.Report.DetailResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Report.SummaryResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Report.TopProductResponse;
import cn.edu.xidian.tafei_mall.model.vo.Response.Report.createReportResponse;
import cn.edu.xidian.tafei_mall.service.ReportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public createReportResponse createMonthlyReport(int year, int month, boolean detail, String userId){
        List<Order> orders = orderMapper.selectList(new QueryWrapper<Order>()
                .eq("seller_id", userId)
                .apply("YEAR(create_at) = {0}", year)
                .apply("MONTH(create_at) = {0}", month));

        if (orders == null || orders.isEmpty()) {
            throw new RuntimeException("No orders found for the specified period");
        }

        String reportId = UUID.randomUUID().toString();
        String period = String.format("%04d-%02d", year, month);

        // 对所有OrderItem按ProductId分组
        int totalOrders = orders.size();
        Map<String, List<OrderItem>> OrderItemsMap = new HashMap<>();
        double totalSales = 0;
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", order.getOrderId()));
            if (orderItems == null || orderItems.isEmpty()) {
                continue;
            }
            // 按ProductId分组
            for (OrderItem orderItem : orderItems) {
                totalSales += orderItem.getPrice().doubleValue();
                String productId = orderItem.getProductId();
                if (!OrderItemsMap.containsKey(productId)) {
                    OrderItemsMap.put(productId, new ArrayList<>());
                }
                OrderItemsMap.get(productId).add(orderItem);
            }
        }
        // 处理分组后的数据
        String topProductId = null;
        int TopProductSalesCount = 0;
        List<Double> productAveRatings = new ArrayList<>();
        List<DetailResponse> detailResponses = new ArrayList<>();
        for (Map.Entry<String, List<OrderItem>> entry : OrderItemsMap.entrySet()) {
            // 对于每件商品
            String productId = entry.getKey();
            List<OrderItem> orderItems = entry.getValue();
            // 计算商品的平均评分
            List<Review> reviews = reviewMapper.selectList(new QueryWrapper<Review>().eq("product_id", productId));
            int  totalRating = 0;
            for (Review review : reviews) {
                totalRating += review.getRating();
            }
            double averageRating = !reviews.isEmpty() ? (double) totalRating / reviews.size() : 0;
            productAveRatings.add(averageRating);
            // 计算商品的销售数量，以获得销售数量最大的商品
            int productSalesCount = 0;
            double productTotalRevenue = 0;
            for (OrderItem orderItem : orderItems) {
                productSalesCount += orderItem.getQuantity();
                productTotalRevenue += orderItem.getPrice().doubleValue();
            }
            if (productSalesCount > TopProductSalesCount) {
                TopProductSalesCount = productSalesCount;
                topProductId = productId;
            }
            // 当detail = true时，添加详细报告
            if (detail) {
                Product product = productMapper.selectById(productId);
                if (product != null) {
                    String productName = product.getName();
                    String promotionId = null;
                    // 此处处理促销ID
                    detailResponses.add(new DetailResponse(productId, productName, productSalesCount, productTotalRevenue, (float)averageRating, promotionId));
                }
            }
        }
        String topProductName = productMapper.selectById(topProductId).getName();
        double averageRating = productAveRatings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // 创建报告
        if (detail){
            // 详细报告
            return new createReportResponse(reportId, period, new SummaryResponse(totalSales, totalOrders, (float)averageRating, new TopProductResponse(topProductId, topProductName, TopProductSalesCount)), detailResponses);
        } else {
            // 简要报告
            return new createReportResponse(reportId, period, new SummaryResponse(totalSales, totalOrders, (float)averageRating, new TopProductResponse(topProductId, topProductName, TopProductSalesCount)), null);
        }
    }
}
