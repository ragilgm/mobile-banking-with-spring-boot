package com.finalproject.ragil.finalproject;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinalprojectApplicationTests {

//	@Autowired
//	 MbankingAccountRepository repo;
//	
//	@MockBean	
//	 MBankingAccountMapper mapper;
//	
//	private MockMvc
//	@Test
//	public void registerMbankingTest() {
//		MbankingAccount account = new MbankingAccount(1, "hello", "hello", "hello", new Date(), "hello", "hello", "hello", "hello");
//		when(mapper.registerMbankingAccount(account)).thenReturn(1);
//		assertEquals(1, repo.registerMbankingAccount(account));
//	}
//	

}


//@Insert("insert into mobile_banking (accountid,accountnumber,pin,email,dateofbirth,ektp,usernameid,mpin,status)values (#{accountid},#{accountnumber},#{pin},#{email},#{dateofbirth},#{ektp},#{usernameid},#{mpin},#{status})")
//int registerMbankingAccount(MbankingAccount account);
//
//@Update("update mobile_banking set status=#{status} where usernameid = #{usernameid}")
//int updateMbankingAccount(MbankingAccount account);
//
//@Select("select * from account where accountnumber=#{accountnumber} and pin=#{pin}"
//		+ " and email=#{email} and ektp=#{ektp}")
//Account checkAccount(MbankingAccount account);
//
//@Select("select * from mobile_banking where accountnumber=#{accountnumber}")
//Account checkMobileBankingAccount(MbankingAccount account);
//
//@Select("select * from mobile_banking where usernameid=#{usernameid} and mpin=#{mpin}")
//MbankingAccount accountLogin(MbankingAccount account);
//
//@Select("select * from account where pin=#{pin} and accountnumber=#{accountnumber}")
//Account accountInformation(MbankingAccount account);
//
//@Select("select * from mobile_banking where usernameid=#{usernameid} and pin=#{pin}")
//MbankingAccount userInformation(String usernameid, String pin);
//
//@Insert("insert into transaction (transactionid,accountid,merchantid,merchantname,ammount, tax, date) values (#{transactionid},#{accountid},#{merchantid},#{merchantname},#{ammount},#{tax},#{date})")
//int transaction(Transaction transaction);
//
//@Update("update account set balance=#{balance} where accountnumber=#{accountnumber}")
//int updateBalance(int balance, String accountnumber);
//
//@Select("select * from mobile_banking where usernameid=#{username}")
//MbankingAccount findByUsername(String username);