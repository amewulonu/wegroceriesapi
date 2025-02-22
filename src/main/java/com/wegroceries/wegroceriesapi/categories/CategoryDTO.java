package com.wegroceries.wegroceriesapi.categories;

public class CategoryDTO {

    private Long id;
    private String name;
    private String description;

    // Default constructor
    public CategoryDTO() {
    }

    // Parameterized constructor
    public CategoryDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Override toString method
    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    // Override equals method (optional, useful if comparing DTOs)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return id.equals(that.id);
    }

    // Override hashCode method (optional, useful if using in collections)
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

