package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ApiModel(description = "we have product model class in our application")
public class Product {
	
	@ApiModelProperty(position=0,notes="Product Id is unique and PK")
	@Id
	private @NonNull int productId;
	
	@ApiModelProperty(position=1,required = true,  notes="Product Name")
	@NotNull
	private String productName;
	
	@ApiModelProperty(position=2,required = true, notes="Product quantity on hand")
	private @NonNull int quantityOnHand;
	
	@ApiModelProperty(position=3,required = true, notes="Product price")
	private @NonNull int price;
		
    private @NonNull String name;		
	
	
}
