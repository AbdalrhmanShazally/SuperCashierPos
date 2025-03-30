package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Statement;

import common.Common;
import database.DBInitialize;
import database.DBIntialize2;
import functs.ReportGenerator;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.CardUser;
import net.sf.jasperreports.engine.JRException;

public class CardPayController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField tf_purchase_id;

    @FXML
    private JFXTextField tf_total_amount;

    @FXML
    private JFXTextField tf_card_no;

    @FXML
    private JFXButton bt_pay;
    
    @FXML
    private JFXTextField tf_pay_amount;
    
    
    @FXML
    private JFXRadioButton btn_rdo_cash;
	
	
    @FXML
    private JFXRadioButton btn_rdo_transfer;	

    String purchasedate;
   
    String cardno;
    
    private double payamount;
    double cardamount;

    @FXML
    void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        assert tf_purchase_id != null : "fx:id=\"tf_purchase_id\" was not injected: check your FXML file 'Card_pay.fxml'.";
        assert tf_total_amount != null : "fx:id=\"tf_total_amount\" was not injected: check your FXML file 'Card_pay.fxml'.";
        assert tf_card_no != null : "fx:id=\"tf_card_no\" was not injected: check your FXML file 'Card_pay.fxml'.";
        assert bt_pay != null : "fx:id=\"bt_pay\" was not injected: check your FXML file 'Card_pay.fxml'.";
        assert tf_pay_amount != null : "fx:id=\"tf_pay_amount\" was not injected: check your FXML file 'Card_pay.fxml'.";

        btn_rdo_cash.setSelected(true);
        tf_card_no.setEditable(false);
        tf_pay_amount.setEditable(true);
       
        /*new Thread(() -> {
        	try {
				ServerSocket ss = new ServerSocket(5000);
			    System.out.println("Server is running at port : 5000");
        
        		while(true) {
        			Socket s = ss.accept();
        			DataInputStream inputFromClient = new DataInputStream( s.getInputStream());
    				
        			String message =inputFromClient.readUTF();
                    System.out.println("Received from android: " + message);
                   // inputFromClient.close();
                   // s.close();
    				
    				
    				Platform.runLater(()->
    					tf_card_no.setText(""+message));
    				
    	        }
	        	 
        	}
        	catch(Exception ex) {
        		
        	}
        }).start();
*/
        tf_total_amount.setText(""+Common.totalAmount);
        
        //get purchase id from previous row
        //get previous id and create now id
  		new DBInitialize().DBInitialize();
  		String previousgetpurchaseid = " SELECT `id` FROM `purchase` ORDER BY `id` DESC LIMIT 1 ";
  		new DBInitialize();
  		ResultSet rs = DBInitialize.statement.executeQuery(previousgetpurchaseid);
  		String previousid = "";
  		while(rs.next()) {
  			previousid = rs.getString("id");
  		}
  		int nowid = Integer.parseInt(previousid) + 1;
  		
  		tf_purchase_id.setText(""+nowid);
  		Common.purchaseid = nowid;
  		
  		/* commented by a.shazally as my clients dont issue cards or use them 
  		tf_card_no.setOnAction(e->{
  			 cardno = tf_card_no.getText().toString();
  			String getCardInfoQuery = "SELECT * FROM `Card` WHERE Card.cardnumber = '"+cardno+"';";
  			
  			CardUser c = new CardUser();
  			try {
				new DBInitialize().DBInitialize();
				new DBInitialize();
				ResultSet rsc = DBInitialize.statement.executeQuery(getCardInfoQuery);
				if(rsc.next()) {
					
					c.setCardno(rsc.getString(1));
					c.setCustomrid(rsc.getString(2));
					c.setAmount(rsc.getString(3));
					c.setLastdateused(rsc.getString(4));
					c.setRegisterdate(rsc.getString(5));
					c.setExpireddate(rsc.getString(6));
					c.setPin(""+rsc.getInt(7));
					
					
					double totalamount = Double.parseDouble(tf_total_amount.getText().toString());
		  			 cardamount = Double.parseDouble(c.getAmount());
		  			
		  			if(totalamount> cardamount) {
		  				Alert al = new Alert(AlertType.ERROR, "Insufficient Card Balance!");
						al.showAndWait();
		  			}
		  			else {
		  				
		  				double tominus = (totalamount) * (0.15);
		  				System.out.println("to minus is : "+tominus);
		  				 payamount = totalamount - tominus;
		  				System.out.println("payamount is : "+payamount);
		  				tf_pay_amount.setText(""+payamount);
		  				Common.payamount = payamount;
		  			}
		  			///
				}else {
					Alert al = new Alert(AlertType.ERROR, "Invalid Card!");
					al.showAndWait();
				}
				
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  			
  			
  		});
  		*/
  		
  		//added by a.shazally:
  	    ToggleGroup PayType = new ToggleGroup();
  	    btn_rdo_cash.setToggleGroup(PayType);
  	    btn_rdo_transfer.setToggleGroup(PayType);
  	    
  	    //add listener : 
  	    PayType.selectedToggleProperty().addListener((Observable, oldValue, newValue) -> {
  	    	if(btn_rdo_cash.isSelected()) {
  	    		Common.paidtype = "Cash";
  	    		cardno = "999";
  	    		tf_card_no.setDisable(true);
  	    		tf_pay_amount.requestFocus();
  	    		
  	    	} else {
  	    		Common.paidtype = "Bank";
  	    		cardno = tf_card_no.getText().toString();
  	    		tf_card_no.setDisable(false);
  	    		tf_card_no.setEditable(true);
  	    		tf_card_no.requestFocus();
  	    	}
  	    });
        
  	// Ensure the initial state is set correctly
        if (btn_rdo_cash.isSelected()) {
            tf_card_no.setDisable(true);
            tf_pay_amount.requestFocus();
        } else {
            tf_card_no.setDisable(false);
            tf_card_no.requestFocus();
        }
    }
    
    

    
    @FXML
    void onbtPayAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
 
    	
    	/*ObservableList<Sale> saledata = FXCollections.observableArrayList();
    	saledata = Common.saleitemsdatafromsaletable;*/
    	
    	if(tf_pay_amount.getText().isEmpty()) {
    		//do nothing
    	}else {
    		
    		//set payamount:
    		payamount = Double.parseDouble(tf_pay_amount.getText());
    		
    		//added by a.ashazally in case cash transaction skip the tf_card_no filling:
    		
    		if (btn_rdo_transfer.isSelected()) {
    			String cardinfoforprint = tf_card_no.getText().toString();
        		Common.cardinfo = "A/C    XXXXXX XXX XXXX XX "+ cardinfoforprint.substring(cardinfoforprint.length() - 4);
        	
    		} else {
    			Common.cardinfo = "Cash transaction";
    		}
    		
    		// Create a single DBInitialize instance
    		DBIntialize2 db = new DBIntialize2();
    	    Statement statement = db.getStatement(); //Ensure statement is initialized once
    		
    	//	String cardinfoforprint = tf_card_no.getText().toString();
    	//	Common.cardinfo = "A/C    XXXXXX XXX XXXX XX "+ cardinfoforprint.substring(cardinfoforprint.length() - 4);
    	
    	
    	//get purchase id from previous row
        //get previous id and create now id
  		//new DBInitialize().DBInitialize();
  		String previousgetpurchaseid = " SELECT `id` FROM `purchase` ORDER BY `id` DESC LIMIT 1 ";
  		//new DBInitialize(); a.shazally 29-03-25
  		//ResultSet rs = DBInitialize.statement.executeQuery(previousgetpurchaseid); a.shazally 29-03-25
  		ResultSet rs = statement.executeQuery(previousgetpurchaseid);
  		String previousid = "";
  		while(rs.next()) {
  			previousid = rs.getString("id");
  		}
  		int nowid = Integer.parseInt(previousid) + 1;
  		System.out.println("purchase id for now is : "+nowid);
  		Common.purchaseid = nowid;
  		
  		//create today date
  		String pattern = "dd/MM/yyyy";
		 purchasedate =new SimpleDateFormat(pattern).format(new Date());
		System.out.println("date purchase is "+purchasedate);
		
		//create today current time(purhcase time)
		 String hour = ""+new Date().getHours();
		 String min = ""+new Date().getMinutes();
		String time  = hour+" : "+min;
	    System.out.println("purhcase time is : "+time);
		
	   
	    
		//db
    //	new DBInitialize().DBInitialize();
    //	new DBInitialize();
    	String querycreatepurchase = "INSERT INTO `purchase`(`id`, `date`, `time`, `cashierid`, `barcode`, `quantity`, `totalamount` ) "
    			+ "VALUES ("+Common.purchaseid+", '"+purchasedate+"', '"+time+"',"+Common.cashierrec.getId()+",'"+Common.productids+"','"+Common.productqtys+"','"+payamount+"')";
    	//db.statement.executeUpdate(querycreatepurchase); a.shazally 29-03-25
    	statement.executeUpdate(querycreatepurchase);
    	
    	
    	//get old transaction id and count +1 for new INSERT
    	String getTranasctionIDQuery = "SELECT `id` FROM `transaction` ORDER BY transaction.id DESC LIMIT 1";
    	//new DBInitialize();
    	//ResultSet rstid = db.statement.executeQuery(getTranasctionIDQuery); a.shazally 29-03-25
    	ResultSet rstid = statement.executeQuery(getTranasctionIDQuery);
    	String oldtid = "";
    	while (rstid.next()) {
    		oldtid = rstid.getString(1);
    	}
    	String newtid = ""+(Integer.parseInt(oldtid) + 1);
    	
    	//add to transaction table
    	String addtransactionquery = "INSERT INTO `transaction`(`id`, `cashierid`, `cardid`, `purchaseid`, `amount`) VALUES ('"+newtid+"','"+Common.cashierrec.getId()+"','"+cardno+"','"+Common.purchaseid+"','"+payamount+"')";
    	//new DBInitialize();
    	//db.statement.executeUpdate(addtransactionquery);  a.shazally 29-03-25
    	statement.executeUpdate(addtransactionquery);
    	
    	//compute the new card balance amount
    	//Commented by a.shazally:
    	/*
    	double newbalance = cardamount - payamount;
    	String reductcardmoneyquery = "UPDATE `Card` SET `amount`='"+newbalance+"',`lastuseddate`='"+purchasedate+"' WHERE Card.cardnumber = '"+cardno+"';";
    	new DBInitialize();
    	DBInitialize.statement.executeUpdate(reductcardmoneyquery);
    	*/
    	
    	//update count and stock amount
    	//get all the purchase id and count +1 in db
    	String[] purchasedproductitemsid = Common.productids.split(",");
    	String[] purchasedproductitemsqty = Common.productqtys.split(",");
    	
    	for(int i=0; i< purchasedproductitemsid.length; i++) {
    		int oldcount = 0 ;
    		int newcount = 0;
    		//get old count
    		String getOldCountQuery = "SELECT `count` FROM `productitems` WHERE productitems.barcode = '"+purchasedproductitemsid[i]+"';";
    		System.out.println("product barcode is "+purchasedproductitemsid[i]);
    		//new DBInitialize().DBInitialize();
    		//new DBInitialize();
    		//ResultSet rsoldc = db.statement.executeQuery(getOldCountQuery); a.shazally 29-03-25
    		ResultSet rsoldc = statement.executeQuery(getOldCountQuery);
    		while(rsoldc.next()) {
    			oldcount = rsoldc.getInt(1);
    		}
    		System.out.println("old count is : "+oldcount);
    		
    		newcount  = oldcount + Integer.parseInt(purchasedproductitemsqty[i]);
    		System.out.println("new count is "+newcount+"purchase qty"+purchasedproductitemsqty[i]);
    		String updatecountQuery = "UPDATE `productitems` SET `count`= "+newcount+" WHERE productitems.barcode = '"+purchasedproductitemsid[i]+"'";
    		//new DBInitialize().DBInitialize();
    		//new DBInitialize();
    		//db.statement.executeUpdate(updatecountQuery); a.shazally 29-03-25
    		statement.executeUpdate(updatecountQuery);
    		
    		//get stock amount
    		String getstockquery = "SELECT `stockamount` FROM `productitems` WHERE productitems.barcode = '"+purchasedproductitemsid[i]+"';";
    		String oldstock = ""; 
    		//new DBInitialize();
    		//ResultSet rsst = db.statement.executeQuery(getstockquery); a.shazally 29-03-25
    		ResultSet rsst = statement.executeQuery(getstockquery);
    		while(rsst.next()) {
    			oldstock = rsst.getString(1);
    		}
    		String newstock = ""+(Integer.parseInt(oldstock) - Integer.parseInt(purchasedproductitemsqty[i]));
    		//update stock
    		String updatestockquery = "UPDATE `productitems` SET `stockamount`='"+newstock+"' WHERE productitems.barcode = '"+purchasedproductitemsid[i]+"'";
    		//new DBInitialize();
    		//db.statement.executeUpdate(updatestockquery);  a.shazally 29-03-25
    		statement.executeUpdate(updatestockquery);
    	
    	}//end of for
    	
    	/*System.out.println("qty is: ");
    	for(int i = 0; i< purchasedproductitemsqty.length ;  i++) {
    		System.out.println(""+purchasedproductitemsqty[i]);
    	}
    	System.out.println("length of id and qty are : "+purchasedproductitemsid.length+"  & "+purchasedproductitemsqty.length);*/
    	//MainCashierController.clearsaletableitems();
    	//((Stage)bt_pay.getScene().getWindow()).close();
    	//alert
       //	Alert trancompleteal = new Alert(AlertType.INFORMATION, "Transaction Complete! "+payamount+" Click Ok to print voucher.");
     //	trancompleteal.showAndWait();
    
    	
    	//generate report
    	try {
			new ReportGenerator().generatevoucher(Common.saleitemsdatafromsaletable);
			String commitQuery = "commit";
			//new DBInitialize();
			//db.statement.executeUpdate(commitQuery);  a.shazally 29-03-25
			statement.executeUpdate(commitQuery);
			db.close();
			((Stage)bt_pay.getScene().getWindow()).close();
	    	//alert
	    	Alert trancompleteal = new Alert(AlertType.INFORMATION, "Transaction Complete! "+payamount+" Click Ok to print voucher.");
	    	trancompleteal.showAndWait();
			//trancompleteal.close();
		} catch (JRException | FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			String rollbackQuery = "rollback";
			//new DBInitialize();
			//db.statement.executeUpdate(rollbackQuery);  a.shazally 29-03-25
			statement.executeUpdate(rollbackQuery);
			db.close();
			((Stage)bt_pay.getScene().getWindow()).close();
	    	//alert
	    	Alert tranfail = new Alert(AlertType.ERROR, "Transaction Failed! "+ e.getMessage());
	    	tranfail.showAndWait();
			e.printStackTrace();
		}
    	
    	}//end of if check tfamount is empty
    }
}

