import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-new-level',
  templateUrl: './new-level.component.html',
  styleUrls: ['./new-level.component.scss']
})
export class NewLevelComponent {

  @Input() level: number;
}
