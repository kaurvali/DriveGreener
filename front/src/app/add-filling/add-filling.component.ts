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
    trip: new FormControl({value: 0, disabled: true}),
    distance: new FormControl(0),
    fillingType: new FormControl("full"),
    fuelType: new FormControl<String>(""),
    fuelAmount: new FormControl(0),
    price: new FormControl(0),
    priceControl: new FormControl("full"),
    fuelingNotes: new FormControl(""),
    tires: new FormControl("summer"),
    driving: new FormControl("normal"),
    load: new FormControl("half"),
    cityTime: new FormControl(50),
  });
  maxDate: Date;

  formatLabel(value: number): string {
    return value + "%";
  }

  constructor() {
    // setting the max date to today
    this.maxDate = new Date();
  }

  // TODO - GET VEHICLE FUEL VALUE FROM DB!!!

  isPetrol(){
    //this.filling.get("fuelType")!.setValue("95");
    return false;
  }

  isDiesel(){
    this.filling.get("fuelType")!.setValue("diesel");
    return true;
  }

  isElectric(){
    //this.filling.get("fuelType")!.setValue("electric");
    return false;
  }
}
