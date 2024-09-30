package application.bookstore.models;

import application.bookstore.auxiliaries.FileHandler;
import application.bookstore.views.OrderView;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public  class Order extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    
    //Data needed to make an order
    private String isbn;
    private String clientName;
    private String username;
    private String orderID;
    private int quantity;
    private Book bookSold;
    private String date;
    private float price;
    private float total;
    private int noOfTypes;

    //store them 
    private static final ArrayList<Order> orders = new ArrayList<>();
    public static final String FILE_PATH = "data/orders.ser";
    public static final File DATA_FILE = new File(FILE_PATH);

    
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //change this to dd/mm/yyyy
    private static final DateTimeFormatter idFormatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"); 


    
    //Constructors

	public Order(String isbn, String clientName, int quantity, float price, float total) {
		super(); //Do I have to call this?
		this.isbn = isbn;
		this.clientName = clientName;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
		 LocalDateTime now = LocalDateTime.now();
	     setDate(dtf.format(now));
	     //orderId boyle mi olsun
	     setOrderID("Order_"+idFormatter.format(now));
	}


    //getters & setters
    
    public int getNoOfTypes() {
		return noOfTypes;
	}



	public void setNoOfTypes(int noOfTypes) {
		this.noOfTypes = noOfTypes;
	}



	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Book getBookSold() {
		return bookSold;
	}

	public void setBookSold(Book bookSold) {
		this.bookSold = bookSold;
	}

	
	//What is this for and how does it work?
	public static ArrayList<Order> getSearchResults(String searchText) {
        ArrayList<Order> searchResults = new ArrayList<>();
        for(Order order: getOrders())
            if (order.getClientName().toLowerCase().matches(".*"+searchText.toLowerCase()+".*"))
                searchResults.add(order);
        return searchResults;
    }


    public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}

	//Buraya bir magic dokunus yap
	@Override
    public String toString() {
        String s="Order: "+orderID+"\nDate: "+date+"\nClient: "+clientName+"\nBooks Ordered:"+ quantity + "\n ISBN: "+isbn+"\n Price" + price;
        s+=String.format("\n***************\nTotal: %.2f", total);
        return s;
    }

	
    public static float getTotal(float price, int quantity) throws Exception{
        if(price<0) {
        	throw new Exception("Price cannot be negative");
        }
        else if(quantity<0) {
        	throw new Exception("Quantity cannot be negative");
        }
        float sum=0;
        sum+= quantity * price;
        return sum;
    }
    
    //method to print the data into the file, and also print it to the user too, use stacktrace to identify the error
    public void print(){
    	PrintWriter writer=null;
			try {
				writer = new PrintWriter(new File("bills/" + orderID+".txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	 
        			String isbn= this.getIsbn();
               		String clientName =this.getClientName();
                    float price = this.getPrice();
                    int quantity = this.getQuantity();
                    float total =  price * quantity;
                    writer.print("Order: "+orderID+"\nDate: "+date+"\nClient: "+clientName+"\nBooks Ordered: \n" + quantity + "ISBN: "+isbn+"\n Price" + price +
                    		"\n***************\nTotal: " + total);
             
        	 writer.close();
        	 System.out.println("/n New Bill Printed /n");
        	 System.out.println("bills/" + orderID+".txt");
        	 System.out.println(this.toString());
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean saveInFile() {
        boolean saved = super.save(Order.DATA_FILE);
        if (saved)
            orders.add(this);
        return saved;
    }

    @Override
    public boolean updateFile() {
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, orders);
        }
        catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public boolean isValid() {
        return clientName.length() > 0;
    }

    @Override
    public boolean deleteFromFile() {
        orders.remove(this);
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, orders);
        }
        catch (Exception e){
            orders.add(this);
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }


    public static ArrayList<Order> getOrders() {
        if (orders.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Order temp = (Order) inputStream.readObject();
                    if (temp != null)
                        orders.add(temp);
                    else
                        break;
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of orders file reached!");
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return orders;
    }
    
    public void getNewBookStock() {//to find the stock of the corresponding isbn book
		for(Book b: Book.getBooks()) {
			if (b.getIsbn().equals(this.isbn)) {
				b.setStock(b.getStock()-quantity);//set the new stock quantity
			}
		}
		
		}





    public Category getIsbnCategory() {//to find the category of the corresponding isbn book

        for(Book b: Book.getBooks()) {
            if (b.getIsbn().equals(this.isbn)) {
                return b.getCategory();
            }
        }
        return null;
    }





    public int getIsbnStock() {//to find the stock of the corresponding isbn book
		for(Book b: Book.getBooks()) {
			if (b.getIsbn().equals(this.isbn)) {
				return b.getStock();
			}
			}
			return 0;	
		}
}
