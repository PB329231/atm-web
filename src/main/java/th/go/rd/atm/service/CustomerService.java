package th.go.rd.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.go.rd.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private List<Customer> customerList; //เก็บข้อมูล customer ไว้ทุกคน

    @PostConstruct
    public void postConstruct(){
        this.customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer){
        //...hash pin...
        String hashPin = hash(customer.getPin()); //เอา method hashมาใช้ โดยใส่ค่า customer ที่รับค่ามา ส่งเข้าไป
        customer.setPin(hashPin); //เซ็ทค่า pin
        customerList.add(customer); //add customer ลงใน customerlist
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(this.customerList);
    } // คืนค่าเ็น Arraylist customer

    public Customer findCustomer(int id) {
        for (Customer customer : customerList) {
            if (customer.getId() == id)
                return customer;
        }
        return null;
    }

    public Customer checkPin(Customer inputCustomer) {
        // 1. หา customer ที่มี id ตรงกับพารามิเตอร์
        Customer storedCustomer = findCustomer(inputCustomer.getId());

        // 2. ถ้ามี id ตรง ให้เช็ค pin ว่าตรงกันไหม โดยใช้ฟังก์ชันเกี่ยวกับ hash
        if (storedCustomer != null) {
            String storedPin = storedCustomer.getPin();
            if (BCrypt.checkpw(inputCustomer.getPin(), storedPin))
                return storedCustomer;
        }
        // 3. ถ้าไม่ตรง ต้องคืนค่า null
        return null;
    }

    private String hash(String pin){ //ใช้ library ของ BCrypt
        String salt = BCrypt.gensalt(12); // สร้าง salt แล้วเอาไปแปะไว้กับ pin และจะ hash มันอีกรครั้ง
        return  BCrypt.hashpw(pin,salt);
    }
}
