import { Component, OnInit } from '@angular/core';
import { EChartsOption } from 'echarts';
import { FillupService } from '../_services/fillup.service';
import { Graph } from '../_dto/graph.dto';


@Component({
  selector: 'app-filling-graph',
  templateUrl: './filling-graph.component.html',
  styleUrls: ['./filling-graph.component.scss']
})
export class FillingGraphComponent implements OnInit {

  constructor(private fillupService: FillupService){}

  xData: number[][] = [];
  yData: number[] = [];
  graphDataList: Graph[] = [];

  ngOnInit(): void {
    this.fillupService.getFillingPriceGraph(41, 20).subscribe(res => {
      this.graphDataList = res;
      this.getDataForGraph();
      console.log(this.xData, this.yData)
    }); 
  }

  getDataForGraph(){
    if (this.graphDataList.length > 0){
      for (var graph of this.graphDataList) {
        if (graph.odometer && graph.valueDouble){
          this.xData.push([graph.odometer, graph.valueDouble])
        }
        if (graph.valueDouble){
          this.yData.push(graph.valueDouble)
        }
      }
    }

  }

  chartOption: EChartsOption = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'cross' }
    },
    legend: {},
    xAxis: {
      type: 'value',
      name: 'Odometer'
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '{value} €'
      },
      name: 'Price'
    },
    series: [
      {
        data: this.xData,
        type: 'line',
      }
    ],
  };
}
