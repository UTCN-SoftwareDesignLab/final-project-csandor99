package com.example.demo.item;


import com.example.demo.activity.ActivityRepository;
import com.example.demo.activity.ActivityService;
import com.example.demo.activity.model.Activity;
import com.example.demo.item.ItemMapper;
import com.example.demo.item.ItemRepository;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import com.example.demo.websocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ActivityService activityService;
    private final WebSocketService webSocketService;

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    private double calculateTotal(List<ItemDTO> list){
        int sum = 0;
        for (ItemDTO i: list) {
            sum += i.getPrice();
        }
        return sum;
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemDTO> filteredItems(String filter){
        return itemRepository.findAllByNameContainingOrBarcodeContaining(filter,filter).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO create(ItemDTO item) {
        return itemMapper.toDto(itemRepository.save(
                itemMapper.fromDto(item)
        ));
    }

    public ItemDTO edit(ItemDTO item) {
        Item updatedItem = findById(item.getId());
        updatedItem.setName(item.getName());
        updatedItem.setBarcode(item.getBarcode());
        updatedItem.setPrice(item.getPrice());
        updatedItem.setQuantity(item.getQuantity());
        return itemMapper.toDto(
                itemRepository.save(updatedItem)
        );
    }

    public void sell(String name, List<ItemDTO> list) {
        boolean isOutOfStock = false;
        StringBuilder stringBuilder = new StringBuilder();
        List<ItemDTO> list1 = new ArrayList<>();

        for (ItemDTO i: list) {
            if(i.getQuantity() > 0) {
                if(i.getQuantity() == 1){
                    isOutOfStock = true;
                    stringBuilder.append(i.getName()).append("; ");

                }
                i.setQuantity(i.getQuantity() - 1);
                this.edit(i);
                list1.add(i);
            }

        }
        double tot = calculateTotal(list1);
        Activity activity = Activity.builder().employeeName(name).date(LocalDate.now()).total(tot).build();
        if((activityService.countEmployeeActivity(name) + 1) % 10 == 0){
            activity.setTotal(tot - (tot * 0.2));
        }
        activityService.create(activity);
        if(isOutOfStock){
            webSocketService.sendMessage(stringBuilder.toString());
        }

    }

    public List<ItemDTO> outOfStock(){
        return itemRepository.findAllByQuantityEquals(0).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO get(Long id) {
        return itemMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

}
