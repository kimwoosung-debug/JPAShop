package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.BookForm;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String newItem(Model model) {

        model.addAttribute("form", new BookForm());
        return "Items/createItemForm";
    }

    @PostMapping("/items/new")
    public String saveItem(@Valid BookForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "Items/createItemForm";
        }

        Book item = new Book();
        item.setId(form.getId());
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        item.setAuthor(form.getAuthor());
        item.setIsbn(form.getIsbn());

        itemService.saveItem(item);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "Items/itemList";
    }

    @GetMapping("/items/{id}/edit")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Item items = itemService.findOne(id);
        model.addAttribute("form", items);
        return "Items/updateItem";
    }

    @PostMapping("/items/{id}/edit")
    public String updateList(@PathVariable("id") Long id, @ModelAttribute("form") BookForm form) {

        Book items = (Book) itemService.findOne(id);

        items.setName(form.getName());
        items.setPrice(form.getPrice());
        items.setStockQuantity(form.getStockQuantity());
        items.setAuthor(form.getAuthor());
        items.setIsbn(form.getIsbn());

        itemService.saveItem(items);

        return "redirect:/items";

    }

}
