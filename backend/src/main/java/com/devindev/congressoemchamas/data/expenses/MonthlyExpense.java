package com.devindev.congressoemchamas.data.expenses;

import com.devindev.congressoemchamas.data.legislature.Legislature;
import com.devindev.congressoemchamas.data.politician.Politician;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class MonthlyExpense {

    @Id
    @GeneratedValue
    private Long id;

    private YearMonth yearMonth;

    private double value = 0;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Politician politician;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @JsonManagedReference
    private Legislature legislature;

    @OneToMany(mappedBy = "monthlyExpense", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Expense> expenses = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MonthlyExpense) {
            MonthlyExpense castObj = (MonthlyExpense) obj;
            return (this.getYearMonth().equals(castObj.getYearMonth()));
        } else {
            return super.equals(obj);
        }
    }
}
