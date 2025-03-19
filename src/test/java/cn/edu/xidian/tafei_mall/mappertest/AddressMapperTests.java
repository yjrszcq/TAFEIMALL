package cn.edu.xidian.tafei_mall.mappertest;

    import cn.edu.xidian.tafei_mall.mapper.AddressMapper;
    import cn.edu.xidian.tafei_mall.model.entity.Address;
    import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
    import org.springframework.boot.test.context.SpringBootTest;

    import static org.assertj.core.api.Assertions.assertThat;

    @MybatisPlusTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public class AddressMapperTests {

        @Autowired
        private AddressMapper addressMapper;

        @Test
        void testInsertAddress() {
            Address address = new Address();
            address.setAddress("123 Main St");

            int result = addressMapper.insert(address);
            assertThat(result).isEqualTo(1);
            assertThat(address.getAddressId()).isNotNull();
        }

        @Test
        void testSelectAddress() {
            Address address = addressMapper.selectById("1");
            assertThat(address).isNotNull();
            assertThat(address.getAddress()).isEqualTo("123 Main St");
        }

        @Test
        void testUpdateAddress() {
            Address address = addressMapper.selectById("1");
            address.setAddress("456 Elm St");
            int result = addressMapper.updateById(address);
            assertThat(result).isEqualTo(1);

            Address updatedAddress = addressMapper.selectById("1");
            assertThat(updatedAddress.getAddress()).isEqualTo("456 Elm St");
        }

        @Test
        void testDeleteAddress() {
            int result = addressMapper.deleteById("1");
            assertThat(result).isEqualTo(1);

            Address address = addressMapper.selectById("1");
            assertThat(address).isNull();
        }
    }