import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemDto } from '../item.model';
import { ButtonComponent } from 'src/app/utils/button/button.component';
import { ShopService } from '../shop.service';
import { HttpErrorResponse } from '@angular/common/http';
import { HeaderService } from '../../header/header.service';
import { SnackbarService } from 'src/app/utils/snackbar/snackbar.service';

@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ItemComponent implements OnInit {
  @Input() item: ItemDto;
  imageName = '';
  loading = false;

  constructor(private shopService: ShopService, private headerService: HeaderService, 
      private snackbarService: SnackbarService) {

  }

  ngOnInit(): void {
    if (this.item.name.includes('potion')){
      this.imageName = 'assets/img/health-potion.png';
    }
  }

  onItemClick() {
    this.loading = true;
    this.shopService.buyItem(this.item)
      .subscribe({
        next: (response) => {
          this.loading = false;
          this.headerService.updateHeader.next();
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
