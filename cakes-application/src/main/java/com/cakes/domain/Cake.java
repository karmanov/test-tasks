package com.cakes.domain;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Cake data model
 */
public class Cake {

    @Id
    private String id;

    @NotNull
    @Size(min = 10, message = "Description should have at least 10 characters")
    private String description;

    private Double price;

    @Min(1)
    private Integer quantity;

    @NotNull
    @Size(min = 2, message = "Image link should have at least 2 characters")
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cake cake = (Cake) o;

        if (id != null ? !id.equals(cake.id) : cake.id != null) return false;
        if (description != null ? !description.equals(cake.description) : cake.description != null) return false;
        if (price != null ? !price.equals(cake.price) : cake.price != null) return false;
        if (quantity != null ? !quantity.equals(cake.quantity) : cake.quantity != null) return false;
        return image != null ? image.equals(cake.image) : cake.image == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                '}';
    }
}
