package org.smart4j.chapter2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;
import org.smart4j.chapter2.utils.DatabaseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JUnit测试
 * Created by alvin on 2016/3/12.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    /**
     * 初始化数据库
     */
    @Before
    public void init(){
        DatabaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    /**
     * 获取客户列表
     * @return
     */
//    @Test
    public void getCustomerListTest(){
        List<Customer> customerList = customerService.getCustomerList();
//        Assert.assertEquals(2, customerList.size());
        for(Customer  customer:customerList){
            System.out.println(customer.toString());
        }
    }

    /**
     * 根据id获取客户
     * @return
     */
    @Test
    public void getCustomerTest(){
        long id = 2;
        Customer customer = customerService.getCustomer(id);
//        Assert.assertNotNull(customer);
        System.out.println(customer.toString());
    }

    /**
     * 创建客户
     * @return
     */
//    @Test
    public void createCustomerTest(){
        Map<String,Object> customer = new HashMap<String, Object>();
        customer.put("name","customer300");
        customer.put("contact","ll ming");
        customer.put("telephone","13838389438");
        boolean result = customerService.createCustomer(customer);
        Assert.assertTrue(result);
    }

    /**
     * 更新客户
     * @return
     */
//    @Test
    public void updateCustomerTest(){
        long id = 1;
        Map<String,Object> customer = new HashMap<String, Object>();
        customer.put("name","customer880");
        boolean result = customerService.updateCustomer(id, customer);
        Assert.assertTrue(result);
    }

    /**
     * 删除客户
     * @return
     */
//    @Test
    public void deleteCustomerTest(){
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }
}
