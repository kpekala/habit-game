import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ButtonComponent } from 'src/app/utils/ui/button/button.component';
import { PlayerItemDto } from '../../player/user.model';
import { ItemDto } from '../item.model';

@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ItemComponent implements OnInit {
  @Input() item: ItemDto | PlayerItemDto;
  @Input() loading = false;
  @Output() onClick = new EventEmitter<ItemDto | PlayerItemDto>();
  imageName = '';

  constructor() {
  }

  ngOnInit(): void {
    if (this.item.name.includes('potion')){
      this.imageName = 'assets/img/health-potion.png';
    }
  }

  onItemClick() {
    this.onClick.emit(this.item);
  }

  shopItem() {
    return this.item as ItemDto;
  }

  eqItem() {
    return this.item as PlayerItemDto;
  }

  buttonTitle() {
    return 'cost' in this.item ? 'Buy': 'Use';
  }

  isShopItem() {
    return 'cost' in this.item;
  }

}
