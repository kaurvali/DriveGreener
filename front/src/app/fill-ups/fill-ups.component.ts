import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FillupStatistics } from '../_dto/fillup.statistics.dto';
import { UserService } from '../_services/user.service';
import { VehicleService } from '../_services/vehicle.service';

@Component({
  selector: 'app-fill-ups',
  templateUrl: './fill-ups.component.html',
  styleUrls: ['./fill-ups.component.scss']
})
export class FillUpsComponent implements OnInit {
getTrip() {
throw new Error('Method not implemented.');
}

  constructor(private userService: UserService, private vehicleService: VehicleService, private router: Router, private route: ActivatedRoute) { }

  vehicle: any = -1;
  fillups: FillupStatistics[] = [];
  vehicleData: any;

  ngOnInit() {
    // getting the vehicle id from url
    this.route.params.subscribe(params => {
      this.vehicle = +params['id'];
    });

    this.vehicleService.getFillupsForVehicle(this.vehicle).subscribe(res => {
      this.fillups = res;
      console.log(this.fillups)
    }); 

    this.vehicleService.getVehicle(this.vehicle).subscribe(res => {
      this.vehicleData = res;
    }); 

  }

  addFillup(): void {
    this.router.navigate(["/add-fillup", this.vehicle]);
  }

  getName(): string {
    if (this.vehicleData)
      return this.vehicleData.year + " " + this.vehicleData.make + " " + this.vehicleData.model + " " + this.vehicleData.trim;
    return "";
  }
}
