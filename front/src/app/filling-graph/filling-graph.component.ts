import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { FillupService } from '../_services/fillup.service';
import { Graph } from '../_dto/graph.dto';

import * as echarts from 'echarts';

@Component({
  selector: 'app-filling-graph',
  templateUrl: './filling-graph.component.html',
  styleUrls: ['./filling-graph.component.scss']
})
export class FillingGraphComponent implements OnInit {

  @Input() vehicleID: number = -1;
  @Input() maxResults: number = 50;
  @Input() type: string = "";


  constructor(private fillupService: FillupService){}

  graphData: number[][] = [];
  isLoading = true;
  yUnit = "€"
  xUnit = "km"
  yLabel = "Price"
  xLabel = ""
  description = "Price per filling"

  ngOnInit(): void {
    this.fillupService.getLineGraph(this.type, this.vehicleID, this.maxResults).subscribe({
      next: (res) => {
        console.log(res);
        this.graphData = this.getDataForGraph(res);
        if (res.description)
          this.description = res.description;
        if (res.xunit){
          this.xUnit = res.xunit;
        }
        if (res.yunit){
          this.yUnit = res.yunit;
        }
        if (res.ylabel){
          this.yLabel = res.ylabel;
        }      
        if (res.xlabel){
          this.xLabel = res.xlabel;
        }
      },
      error: (err) => console.error(err),
      complete: () => {
        this.isLoading = false;
        var chartDom = document.getElementById(this.type+"-"+this.vehicleID)!;
        var myChart = echarts.init(chartDom, undefined, { renderer: 'svg' });
        myChart.setOption(this.getGraphOptions());
        window.addEventListener('resize', function() {
          myChart.resize();
        });
       }
    }
    ); 
  }

  getDataForGraph(res: Graph){
    var out: number[][] = []
    if (res.odometer && res.valueDouble){
      for (let i = 0; i < res.odometer.length; i++){
        out.push([res.odometer[i], res.valueDouble[i]])
      }
    }
    return out;
  }

  getGraphOptions(){
    var odometerList = this.graphData.map(function (item) {
      return item[0];
    });
    var valueList = this.graphData.map(function (item) {
      return item[1];
    });

    var option = {
      title: {
        text: this.description,
        textStyle: {
          color: '#fff',
          FontFace: "Roboto"
        }
      },
      grid: { 
        containLabel: true, 
        left: '1%',
        right: '1%',
        bottom: '1%',
        top: '25%',
      },
      textStyle: {
        fontSize: '14',
      },
      tooltip: {
        trigger: 'axis',
        formatter: "{c}" + this.yUnit + "<br>{b}" + this.xUnit,
      },
      legend: {},
      xAxis: [{
        type: 'category',
        data: odometerList,
        axisLabel: {
          formatter: '{value}'+this.xUnit,
          fontSize: 14,
        },
        name: this.xLabel,
      }],
      yAxis: [{
        type: 'value',
        min: function (value: { min: number; }) {
          return Math.round(value.min - Math.round(value.min*0.5));
        },
        axisLabel: {
          formatter: '{value}'+this.yUnit,
          fontSize: 14,
          hideOverlap: true,
        },
        name: this.yLabel,

      }],
      series: [
        {
          data: valueList,
          color: '#607d8b',
          symbolSize: 8,
          type: 'line',
          lineStyle: {
            color: '#607d8b',
            width: 4,
          },
        }
      ],
    };
    return option;
  }
}
