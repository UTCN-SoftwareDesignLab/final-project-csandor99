package com.example.demo.item;


import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDTO toDto(Item item);
    Item fromDto(ItemDTO item);

}
