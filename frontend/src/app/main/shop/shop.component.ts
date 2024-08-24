import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { SnackbarService } from 'src/app/utils/snackbar/snackbar.service';
import { UserService } from '../player/user.service';
import { ItemDto } from './item.model';
import { ItemComponent } from './item/item.component';
import { ShopService } from './shop.service';

@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [CommonModule, ItemComponent],
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent implements OnInit {

  items: ItemDto[] | undefined = undefined;

  loading = false;

  constructor(private shopService: ShopService, private snackbarService: SnackbarService, private readonly userService: UserService) {}

  ngOnInit(): void {
    this.shopService.fetchItems().subscribe({
      next: (items) => {
        this.items = items;
      }
    });
  }

  onItemBuy(item: ItemDto) {
    this.loading = true;
    this.shopService.buyItem(item)
      .subscribe({
        next: (response) => {
          this.loading = false;
          this.userService.fetchUserInformation().subscribe();
          this.snackbarService.success('You succesfully bought a potion!');
      },error: (errorResponse: HttpErrorResponse) => {
        this.loading = false;
        if(errorResponse.error.message === 'Not enough gold!') {
          this.snackbarService.error('Not enough gold!');
        }
      }
    });
  }
}
