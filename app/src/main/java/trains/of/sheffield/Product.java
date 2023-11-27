package trains.of.sheffield;
public class Product {
    private String productCode;
    private String brandName;
    private String productName;
    private Double price;
    private Gauge gauge;
    private String description;
    private Integer stock;

    public Product(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock) {
        this.productCode = productCode;
        this.brandName = brandName;
        this.productName = productName;
        this.price = price;
        this.gauge = gauge;
        this.description = description;
        this.stock = stock;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public Gauge getGauge() {
        return gauge;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}

