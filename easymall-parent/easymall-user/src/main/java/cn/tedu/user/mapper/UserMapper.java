package cn.tedu.user.mapper;

import com.jt.common.pojo.User;
import org.apache.ibatis.annotations.Delete;

public interface UserMapper {

    int selectCountByUserName(String userName);

    void insertUser(User user);

    User selectUserByUserNameAndPassword(User user);
}
