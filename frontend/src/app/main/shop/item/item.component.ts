import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemDto } from '../item.model';
import { ButtonComponent } from 'src/app/utils/button/button.component';
import { ShopService } from '../shop.service';
import { HttpErrorResponse } from '@angular/common/http';
import { HeaderService } from '../../header/header.service';
import { SnackbarService } from 'src/app/utils/snackbar/snackbar.service';
import { PlayerItemDto } from '../../player/user.model';

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
  @Output() click = new EventEmitter<ItemDto | PlayerItemDto>();
  imageName = '';

  constructor() {
  }

  ngOnInit(): void {
    if (this.item.name.includes('potion')){
      this.imageName = 'assets/img/health-potion.png';
    }
  }

  onItemClick() {
    this.click.emit(this.item);
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
