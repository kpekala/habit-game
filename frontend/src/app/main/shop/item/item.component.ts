import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemDto } from '../item.model';
import { ButtonComponent } from 'src/app/utils/button/button.component';

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

  ngOnInit(): void {
    if (this.item.name.includes('potion'))
      this.imageName = 'assets/img/health-potion.png';
  }
}
