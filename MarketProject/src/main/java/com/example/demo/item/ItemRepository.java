package com.example.demo.item;

import com.example.demo.item.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    List<Item> findAllByNameContainingOrBarcodeContaining(String name, String barcode);

    List<Item> findAllByQuantityEquals(int val);

   // List<Book> findAllByNameLikeOrderByNameDesc(String name);

    // or, more dynamically...
    //List<Book> findAllByNameLike(String name, Sort sorting);

    //Page<Book> findAllByNameLike(String name, Pageable pageable);

    //Page<Book> findAllByDescriptionLike(String description, Pageable pageable);

    // what if we had 5+ fields to search on...?
    // problem with the fixed set of criterias
    // wouldn't it be cool to have a set of atomic predicates to combine at will?
}
