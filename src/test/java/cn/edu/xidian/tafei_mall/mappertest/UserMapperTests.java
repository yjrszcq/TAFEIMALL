package cn.edu.xidian.tafei_mall.mappertest;

import cn.edu.xidian.tafei_mall.mapper.UserMapper;
import cn.edu.xidian.tafei_mall.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsertUser() {
        User user = new User();
        user.setUserId("shenyaoguan");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        int result = userMapper.insert(user);
        assertThat(result).isEqualTo(1);
        assertThat(user.getUserId()).isNotNull();
    }

    @Test
    void testSelectUser() {
        User user = userMapper.selectById("shenyaoguan");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
    }

    @Test
    void testUpdateUser() {
        User user = userMapper.selectById("shenyaoguan");
        user.setUsername("updateduser");
        int result = userMapper.updateById(user);
        assertThat(result).isEqualTo(1);

        User updatedUser = userMapper.selectById("shenyaoguan");
        assertThat(updatedUser.getUsername()).isEqualTo("updateduser");
    }

    @Test
    void testDeleteUser() {
        int result = userMapper.deleteById("shenyaoguan");
        assertThat(result).isEqualTo(1);

        User user = userMapper.selectById("shenyaoguan");
        assertThat(user).isNull();
    }
}