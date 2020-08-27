package th.go.rd.atm.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.go.rd.atm.model.Customer;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository implements  JdbcRepository{

    private JdbcTemplate jdbcTemplate; //เวลาจะเรียกใช้่ jdbc ในการ execute query

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        String query = "SELECT * FROM customer"; //เขียนกำหนด query ให้กับตัวแปรชื่อ query
        List<Customer> customers =
                jdbcTemplate.query(query, new CustomerMapper()); //ให้ Arraylist เก็บค่า list customer ที่ query ได้มา และ retur ออกมา
        return customers;
    }

    @Override
    public Customer findById(int id) {
        String query = "SELECT * FROM customer WHERE id = " + id; //เขียนกำหนด query ให้กับตัวแปรชื่อ query แต่มีการระบุค่าเลือกเฉพาะรายการ
        Customer customer =
                jdbcTemplate.queryForObject(query, new CustomerMapper()); //queryForObject หาเฉพาะรายการนั้น
        return customer;
    }

    @Override
    public void save(Customer customer) {
        String query = "INSERT INTO customer (id,name,pin) VALUES (?,?,?);";
        Object[] data = new Object[]
                { customer.getId(), customer.getName(), customer.getPin() };
        jdbcTemplate.update(query, data);
    }

    @Override
    public void update(int id, Customer customer) {

    }

    @Override
    public void deleteById(int id) {

    }

    class CustomerMapper implements RowMapper<Customer>{

        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {//ResultSet คือที่ได้มาทั้งหมดเลย

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String pin = resultSet.getString("pin");

            return new Customer(id,name,pin);

        }
    }
}
