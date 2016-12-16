package com.leoni.data.manager;

import com.leoni.data.models.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 18.8.2014
 * Time: 8:54
 * To change this template use File | Settings | File Templates.
 */
@Service("customerManager")
public class CustomerManagerImpl extends GenericManagerImpl<Customer> implements CustomerManager {
}
