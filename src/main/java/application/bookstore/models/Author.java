package application.bookstore.models;

import application.bookstore.auxiliaries.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Author extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private String firstName;

    private String lastName;

    private static final ArrayList<Author> authors = new ArrayList<>();
    public static final String FILE_PATH = "data/authors.ser";
    private static final File DATA_FILE = new File(FILE_PATH);

    @Override
    public String toString() {
        return firstName + "" + lastName;

    }

    public boolean existsInList(){
        for (Author a: authors) {
            if (a.getFullName().equals(this.getFullName()))
                return true;
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public Author(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return getFirstName() + "   " + getLastName();
    }

    public boolean saveInFile() {
        boolean saved = super.save(Author.DATA_FILE);
        if (saved)
            authors.add(this);
        return saved;
    }

    public boolean isValid() {
        return getFirstName().length() > 0 && getLastName().length() > 0;
    }

    @Override
    public boolean deleteFromFile() {
        authors.remove(this);
        try {
            overwriteCurrentListToFile(DATA_FILE, authors);
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Author> getAuthors() {
        if (authors.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Author temp = (Author) inputStream.readObject();
                    if (temp != null)
                        authors.add(temp);
                    else
                        break;
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of author file reached!");
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return authors;
    }

    public static ArrayList<Author> getSearchResults(String searchText) {

        ArrayList<Author> searchResults = new ArrayList<>();
        for(Author author: getAuthors())
            if (author.getFullName().toLowerCase().matches(".*"+searchText.toLowerCase() +".*"))
                searchResults.add(author);
        return searchResults;
    }

    public static<Author extends BaseModel> void overwriteCurrentListToFile(File DATA_FILE, ArrayList<Author> authorList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(DATA_FILE, false);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        if (authorList.size() == 0) {
        } else {
            for (Author author : authorList)
                outputStream.writeObject(author);
        }
    }

    @Override
    public boolean updateFile() {
        try {
            overwriteCurrentListToFile(DATA_FILE, authors);
        }
        catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }
}