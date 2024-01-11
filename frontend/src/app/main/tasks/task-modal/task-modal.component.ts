import { Component, ElementRef, EventEmitter, HostListener, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';

@Component({
  selector: 'app-task-modal',
  templateUrl: './task-modal.component.html',
  styleUrls: ['./task-modal.component.scss']
})
export class TaskModalComponent implements OnInit, OnDestroy{
  @Output() clickOutside = new EventEmitter<any>();
  @ViewChild('card') card: ElementRef;

  readyForClickOutside = false;

  constructor(private eRef: ElementRef) {}

  @HostListener('document:click', ['$event'])
  clickOut(event) {
    if(!this.card.nativeElement.contains(event.target)) {
      // this is workaround for the fact that without this "if" this listener will register the click 
      // which opened this modal.
      if(this.readyForClickOutside) {
        this.clickOutside.emit();
        console.log('outside');
      }
    }
  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    setTimeout(() => {this.readyForClickOutside = true}, 100);
  }

}
