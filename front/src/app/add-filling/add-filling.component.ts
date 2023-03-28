import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router';
import { FillupService } from '../_services/fillup.service';

@Component({
  selector: 'app-add-filling',
  templateUrl: './add-filling.component.html',
  styleUrls: ['./add-filling.component.scss']
})
export class AddFillingComponent implements OnInit {
  
  vehicle: any = -1;
  maxDate: Date;
  constructor(private fillupService: FillupService, private router: Router, private route: ActivatedRoute) {
    // setting the max date to today
    this.maxDate = new Date();
  }

  ngOnInit() {
    // getting the vehicle id from url
    this.route.params.subscribe(params => {
      this.vehicle = +params['id'];
      console.log(this.vehicle);
    });
    this.filling.patchValue({
      vehicleId: this.vehicle,
    });
  }

  filling = new FormGroup({
    time: new FormControl(new Date()),
    trip: new FormControl({value: 0, disabled: true}),
    odometer: new FormControl(0),
    fillupType: new FormControl("FULL"),
    fuelType: new FormControl(""),
    fuelAmount: new FormControl(0),
    price: new FormControl(0),
    priceType: new FormControl("FULL"),
    notes: new FormControl(""),
    tires: new FormControl("SUMMER"),
    drivingStyle: new FormControl("NORMAL"),
    load: new FormControl("HALF"),
    cityDriving: new FormControl(50),
    vehicleId: new FormControl(this.vehicle)
  });

  onSubmit(){
      this.fillupService.addFillup(this.filling);
      this.router.navigate(['/fill-ups', this.vehicle]);
  }

  formatLabel(value: number): string {
    return value + "%";
  }

  // TODO - GET VEHICLE FUEL VALUE FROM DB!!!

  isPetrol(){
    return true;
  }

  isDiesel(){
    return false;
  }

  isElectric(){
    return false;
  }
}
