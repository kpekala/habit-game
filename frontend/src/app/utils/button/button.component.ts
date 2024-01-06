import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent {
  @Input() label: String;
  @Input() isFormButton = true;
  @Input() width = '18rem';
  @Input() disabled = false;
}
