package si.f5.luna3419.lib.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import si.f5.luna3419.lib.utils.ComponentUtil;

public class GUI implements InventoryHolder {
    private final Inventory[] inventories;
    @Getter private final int size;
    @Getter private final String title;

    private int page = 0;

    public GUI(String title, Inventory[] invs) {
        if (invs.length < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        this.size = invs[0].getSize();
        this.title = title;

        List<Inventory> inventories = new ArrayList<>();

        int p = 0;
        for (Inventory inv : invs) {
            Inventory inventory = Bukkit.createInventory(this, size + 9, ComponentUtil.fromString(title));
            
            for (int i = 0; i < size; i++) {
                inventory.setItem(i, inv.getItem(i));
            }

            for (int i = 0; i < 9; i++) {
                ItemStack item = p == 0 || p == (invs.length - 1) ? new ItemStack(Material.GRAY_STAINED_GLASS_PANE) : i == 0 || i == 8 ? new ItemStack(Material.ARROW) : new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta itemMeta = item.getItemMeta();

                itemMeta.displayName(i == 0 && p != 0 && p != (invs.length - 1) ? ComponentUtil.fromString("Back") : i == 8 && p != 0 && p != (invs.length - 1) ? ComponentUtil.fromString("Next") : ComponentUtil.fromString(""));

                item.setItemMeta(itemMeta);

                inventory.setItem(size + i, item);
            }

            inventories.add(inventory);
            p++;
        }

        this.inventories = inventories.toArray(new Inventory[inventories.size()]);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventories[page];
    }

    public boolean hasNext() {
        return inventories.length == size + 1;
    }

    public @NotNull Inventory next() {
        if (!hasNext()) {
            return getInventory();
        }

        page += 1;

        return getInventory();
    }

    public boolean hasPrevious() {
        return size == 0;
    }
    
    public @NotNull Inventory previous() {
        if (!hasPrevious()) {
            return getInventory();
        }

        page -= 1;

        return getInventory();
    }

    public boolean isGui(Inventory inventory) {
        return inventory.getHolder() instanceof GUI;
    }
}
