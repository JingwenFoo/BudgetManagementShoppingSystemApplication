package com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking;

public class Section {
    String category;
    String categoryBudget;

    public Section(String category, String categoryBudget) {
        this.category = category;
        this.categoryBudget = categoryBudget;
    }

    public String getCategoryBudget() {
        return categoryBudget;
    }

    public void setCategoryBudget(String categoryBudget) {
        this.categoryBudget = categoryBudget;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Section{" +
                "category='" + category + '\'' +
                ", categoryBudget='" + categoryBudget + '\'' +
                '}';
    }
}
