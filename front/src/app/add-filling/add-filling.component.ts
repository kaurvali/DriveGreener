import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, MinValidator, Validators } from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router';
import { VehicleStatistics } from '../_dto/vehicle.statistics.dto';
import { FillupService } from '../_services/fillup.service';
import { VehicleService } from '../_services/vehicle.service';

@Component({
  selector: 'app-add-filling',
  templateUrl: './add-filling.component.html',
  styleUrls: ['./add-filling.component.scss']
})
export class AddFillingComponent implements OnInit {
  
  vehicle: any = -1;
  maxDate: Date;
  basicStats: VehicleStatistics = new VehicleStatistics;

  filling = new FormGroup({
    time: new FormControl(new Date()),
    odometer: new FormControl(0, [Validators.min(0)]),
    fillupType: new FormControl("FIRST", [Validators.required]),
    fuelType: new FormControl(null),
    fuelAmount: new FormControl(0.00, [Validators.min(0)]),
    price: new FormControl(0.00, [Validators.min(0)]),
    priceType: new FormControl(null),
    notes: new FormControl(""),
    tires: new FormControl("SUMMER"),
    drivingStyle: new FormControl("NORMAL"),
    load: new FormControl("HALF"),
    cityDriving: new FormControl(50),
    vehicleId: new FormControl(this.vehicle)
  });

  constructor(private fillupService: FillupService, private vehicleService: VehicleService, private router: Router, private route: ActivatedRoute) {
    // setting the max date to today
    this.maxDate = new Date();
  }

  ngOnInit() {
    // getting the vehicle id from url
    this.route.params.subscribe(params => {
      this.vehicle = +params['id'];
    });
    this.filling.patchValue({
      vehicleId: this.vehicle,
    });

    this.vehicleService.getBasicStatisticsForVehicle(this.vehicle).subscribe(
      res =>{
        this.basicStats = res;
        if(this.basicStats.firstDone){
          this.filling.patchValue({
            fillupType: "FULL", 
            odometer: this.basicStats.lastOdometer,
          })
          if(typeof(this.basicStats.lastOdometer) === 'number'){
            this.filling.get('odometer')?.setValidators([Validators.min(this.basicStats.lastOdometer)])
          }
        }
        console.log(res)
        console.log(this.basicStats)
    });
  }

  onSubmit(){
      this.fillupService.addFillup(this.filling).subscribe(
        response => {
          this.router.navigate(['/fill-ups', this.vehicle]);
        }
      );
      
  }

  formatLabel(value: number): string {
    return value + "%";
  }

  getMinDate(){
    if(this.basicStats.firstDone && this.basicStats.lastFilling){
      return this.basicStats.lastFilling;
    }
    return new Date(1800, 0, 1)
  }
}
