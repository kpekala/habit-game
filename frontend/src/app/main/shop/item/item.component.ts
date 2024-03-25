import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemDto } from '../item.model';
import { ButtonComponent } from 'src/app/utils/button/button.component';
import { ShopService } from '../shop.service';

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

  constructor(private shopService: ShopService) {

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
      },error: (errorMsg) => {
        console.log(errorMsg);
          this.loading = false;
      }
    });
  }
}
