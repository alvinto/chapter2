package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.utils.CaseUtil;
import org.smart4j.chapter2.utils.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务
 * Created by alvin on 2016/3/12.
 */
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    /**
     * 获取客户列表
     * @return
     */
    public List<Customer> getCustomerList(){
        String sql = "select * from customer";
        /**常规查询方法
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setTelephone(resultSet.getString("telephone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            logger.error("excute sql failure",e);
        }finally {
            DatabaseHelper.colseConnection(connection);
        }
         */
        return DatabaseHelper.queryEntityList(Customer.class,sql);
    }

    /**
     * 根据id获取客户
     * @param id
     * @return
     */
    public Customer getCustomer(long id){
        String sql = "select * from customer where id = ?";
        return DatabaseHelper.queryEntity(Customer.class,sql,id);
    }

    /**
     * 创建客户
     * @param customer
     * @return
     */
    public boolean createCustomer(Map<String,Object> customer){
//        Connection connection = DatabaseHelper.getConnection();
//        String sql = "insert into customer (name,contact,telephone,email,remark) values(?,?,?,?,?)";
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, CaseUtil.castString(customer.get("name")));
//            preparedStatement.setString(2,CaseUtil.castString(customer.get("contact")));
//            preparedStatement.setString(3,CaseUtil.castString(customer.get("telephone")));
//            preparedStatement.setString(4,CaseUtil.castString(customer.get("email")));
//            preparedStatement.setString(5,CaseUtil.castString(customer.get("remark")));
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            logger.error("insert sql failure", e);
//        }finally {
//            DatabaseHelper.closeConnection();
//        }
//        return true;
        return DatabaseHelper.insertEntity(Customer.class,customer);
    }

    /**
     * 更新客户
     * @param id
     * @param customer Map<String,Object>
     * @return
     */
    public boolean updateCustomer(long id,Map<String,Object> customer){
        return DatabaseHelper.updateEntity(Customer.class,id,customer);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteCustomer(long id){
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }
}
