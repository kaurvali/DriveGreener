import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms'


@Component({
  selector: 'app-add-filling',
  templateUrl: './add-filling.component.html',
  styleUrls: ['./add-filling.component.scss']
})
export class AddFillingComponent {
  filling = new FormGroup({
    date: new FormControl(new Date()),
    amount: new FormControl(""),
    distance: new FormControl(""),
    
  });

  maxDate: Date;

  constructor() {
    // setting the max date to today
    this.maxDate = new Date();
  }
}
