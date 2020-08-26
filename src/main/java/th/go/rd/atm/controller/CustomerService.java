package th.go.rd.atm.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private List<Customer> customerList;

    @PostConstruct
    public void postConstruct(){
        this.customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer){
        //...hash pin...
        customerList.add(customer);
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(this.customerList);
    }
}
