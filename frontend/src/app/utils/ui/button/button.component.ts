import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgStyle } from '@angular/common';

@Component({
    selector: 'app-button',
    templateUrl: './button.component.html',
    styleUrls: ['./button.component.scss'],
    standalone: true,
    imports: [NgStyle]
})
export class ButtonComponent {
  @Input() label: String;
  @Input() isFormButton = true;
  @Input() width = '18rem';
  @Input() disabled = false;
  @Output() onClick = new EventEmitter();
}
