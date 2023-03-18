import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms'


@Component({
  selector: 'app-add-vehicle',
  templateUrl: './add-vehicle.component.html',
  styleUrls: ['./add-vehicle.component.scss']
})
export class AddVehicleComponent{
  vehicle = new FormGroup({
    fuel: new FormControl(""),
    make: new FormControl(""),
    model: new FormControl(""),
    trim: new FormControl(""),
    engine: new FormControl(""),
    power: new FormControl(""),
    transmission: new FormControl(""),
    drivetrain: new FormControl(""),
    public: new FormControl(true),
  });

  constructor() {};

  onSubmit(){};
}
