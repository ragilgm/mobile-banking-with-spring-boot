package com.finalproject.ragil.finalproject.usecase;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.finalproject.ragil.finalproject.entity.CardNumber;
import com.finalproject.ragil.finalproject.entity.Product;
import com.finalproject.ragil.finalproject.entity.Transaction;
import com.finalproject.ragil.finalproject.entity.RequestTransaction;
import com.finalproject.ragil.finalproject.repository.impl.CardNumberRepository;
import com.finalproject.ragil.finalproject.repository.impl.ProductRepository;
import com.finalproject.ragil.finalproject.repository.impl.TransactionRepository;
import com.finalproject.ragil.finalproject.util.Message;
import com.finalproject.ragil.finalproject.util.Status;

@Service
public class ProviderUsecase {

	@Autowired
	ProductRepository productRepo;
	@Autowired
	CardNumberRepository noHpRepo;
	@Autowired
	TransactionRepository transaksiRepo;

	public ResponseEntity<?> showProduct() {
		List<Product> listProduct = productRepo.listProduct();
		return new ResponseEntity<>(listProduct, HttpStatus.OK);
	}

	public ResponseEntity<?> showProductByType(String Type) {
		Product product = new Product();
		product.setType(Type);
		List<Product> listProduct = productRepo.listProductByPaket(product);
		return new ResponseEntity<>(listProduct, HttpStatus.OK);
	}
	

	public ResponseEntity<?> showProductByPrice(String type, int price) {
		Product p = new Product();
		p.setType(type);
		p.setPrice(price);
		Product product = productRepo.singleProductByPrice(p);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}


	public ResponseEntity<?> checkNoHandPhone(String no) {
		CardNumber noHp = new CardNumber();
		noHp.setNo_hp(no);
		CardNumber result = noHpRepo.seachNoHandphone(noHp);
		return new ResponseEntity<>(new Status(result.getStatus()), HttpStatus.OK);
	}

	public ResponseEntity<?> transaksiUsecase(RequestTransaction transaksi) {
		if (transaksi == null) {
			return new ResponseEntity<>(new Message("invalid argument"), HttpStatus.BAD_REQUEST);
		}

		if (transaksi.getType().equals(null) || transaksi.getType().equals("")) {
			return new ResponseEntity<>(new Message("invalid argument"), HttpStatus.BAD_REQUEST);
		}

		if (transaksi.getNo_hp().equals(null) || transaksi.getNo_hp().equals("")) {
			return new ResponseEntity<>(new Message("invalid argument"), HttpStatus.BAD_REQUEST);
		}

		if (transaksi.getId() == 0) {
			return new ResponseEntity<>(new Message("invalid argument"), HttpStatus.BAD_REQUEST);
		}

		CardNumber noHp = new CardNumber();
		noHp.setNo_hp(transaksi.getNo_hp());
		CardNumber resultNoHp = noHpRepo.seachNoHandphone(noHp);
		System.out.println(resultNoHp);
		if (resultNoHp == null) {
			return new ResponseEntity<>(new Message("no hp tidak terdaftar"), HttpStatus.BAD_REQUEST);
		}

		if (resultNoHp.getStatus().equals("blokir")) {
			return new ResponseEntity<>(new Message("no yang anda masukan sudah tidak aktif"), HttpStatus.BAD_REQUEST);
		}

		Product product = new Product();
		product.setType(transaksi.getType());
		product.setId(transaksi.getId());
		
		Product resultProduct = productRepo.singleProduct(product);
		
		if (resultProduct == null) {
			return new ResponseEntity<>(new Message("paket tidak tersedia"), HttpStatus.BAD_REQUEST);
		}

		Transaction addTransaksi = new Transaction();
		addTransaksi.setId_cardnumber(resultNoHp.getId());
		addTransaksi.setId_product(resultProduct.getId());
		addTransaksi.setDate(new Date());
		addTransaksi.setTotal(resultProduct.getPrice());
		System.out.println(addTransaksi);
		int insertToDB = transaksiRepo.insertTransaksi(addTransaksi);
		if (insertToDB == 0) {
			return new ResponseEntity<>(new Message("transaksi gagal"), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(new Message("transaksi berhasil"), HttpStatus.OK);
	}

}
