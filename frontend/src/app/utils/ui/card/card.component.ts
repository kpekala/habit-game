import { Component, ElementRef, EventEmitter, HostListener, OnInit, Output, ViewChild } from '@angular/core';

@Component({
    selector: 'app-card',
    templateUrl: './card.component.html',
    styleUrls: ['./card.component.scss'],
    standalone: true
})
export class CardComponent implements OnInit{

  @Output() onClose = new EventEmitter<boolean>();
  
  @ViewChild('card') card: ElementRef;
  readyForClickOutside = false;

  @HostListener('document:click', ['$event'])
  clickOut(event) {
    if(!this.card.nativeElement.contains(event.target)) {
      // this is a workaround for the fact that without "if" this listener will register the click 
      // which opened this modal.
      if(this.readyForClickOutside) {
        this.onClose.emit();
      }
    }
  }

  ngOnInit(): void {
    setTimeout(() => {this.readyForClickOutside = true}, 100);
  }

}
