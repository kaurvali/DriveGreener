import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Fillup } from '../_models/fillup.model';
import { UserService } from '../_services/user.service';
import { VehicleService } from '../_services/vehicle.service';

@Component({
  selector: 'app-fill-ups',
  templateUrl: './fill-ups.component.html',
  styleUrls: ['./fill-ups.component.scss']
})
export class FillUpsComponent implements OnInit {

  constructor(private userService: UserService, private vehicleService: VehicleService, private router: Router, private route: ActivatedRoute) { }

  vehicle: any = -1;
  public fillups: Fillup[] = [];

  ngOnInit() {
    // getting the vehicle id from url
    this.route.params.subscribe(params => {
      this.vehicle = +params['id'];
    });

    this.vehicleService.getFillupsForVehicle(this.vehicle).subscribe(res => {
      this.fillups = res;
    }); 
  }

  addFillup(): void{
    this.router.navigate(["/add-fillup", this.vehicle]);
  }
}
