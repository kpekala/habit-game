import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShopService } from './shop.service';
import { ItemDto } from './item.model';

@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent implements OnInit {

  items: ItemDto[] | undefined = undefined;

  constructor(private shopService: ShopService) {}

  ngOnInit(): void {
    this.shopService.fetchItems().subscribe({
      next: (items) => {
        this.items = items;
      }
    });
  }
}
