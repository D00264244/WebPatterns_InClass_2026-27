package week03_daos.persistence;

public interface ProductDao {
    boolean addProduct(String productCode, String productName,
                       String productLine, String productScale,
                       String productVendor, String productDescription,
                       int quantityInStock, double buyPrice, double msrp);
}