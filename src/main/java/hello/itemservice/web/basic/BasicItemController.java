package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {
    private final ItemRepository itemRepository;

    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("item1", 5000, 1));
        itemRepository.save(new Item("item2", 10000, 2));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        List<Item> items = itemRepository.findAll();
        Item lastItem = items.get(items.size()-1);
        model.addAttribute("item", lastItem);
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

}
