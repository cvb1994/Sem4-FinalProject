import { Component, OnInit} from '@angular/core';
import { Chart } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import { DashboardService } from 'src/app/service/dashboard.service';
import { UtilitiesService } from 'src/app/service/utilities.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public listTrending : any;
  public totalSong: any;
  public totalAlbum: any;
  public totalUser: any;
  public newAlbum:any;
  public newUser:any;
  public newPayment:any;
  public defaultMonthProfit = 6;
  public listProfit:any;
  public listGenreReport:any;
  public current = new Date();

  constructor(
    private dashboardSer: DashboardService,
    private utilSer : UtilitiesService) { }

  ngOnInit(): void {
    this.initChart();
    this.dashboardSer.getDashboard().subscribe((data) => {
      this.listTrending = data.content.listTrending;
      
      this.totalSong = data.content.totalSong;
      this.totalAlbum = data.content.totalAlbum;
      this.totalUser = data.content.totalUser;
      this.newAlbum = data.content.newAlbumInMonth;
      this.newUser = data.content.newUserInMonth;
      this.newPayment = data.content.newPaymentInMonth;
    });

    this.dashboardSer.getProfitRePort(this.defaultMonthProfit).subscribe((data) =>{
      this.listProfit = data.content;
      this.listProfit.reverse();
      this.listProfit.forEach((item:any) => {
          this.barChart.data.labels.push(item.month+"-"+item.year);
          this.barChart.data.datasets[0].data.push(item.profit);
      });
      this.barChart.update();
    });

    this.dashboardSer.getGenreReport().subscribe((data) =>{
      let listColor = this.utilSer.getListColor();
      this.listGenreReport = data.content;
      let total = 0;
      this.listGenreReport.forEach((item: { count: number; }) =>{
        total = total + item.count;
      });
      this.listGenreReport.forEach((item: { name: any; count: any; })=>{
        if(parseInt(item.count) > 0){
          this.pieChart.data.datasets[0].backgroundColor.push(this.randomColor(listColor));
          this.pieChart.data.labels.push(item.name);
          this.pieChart.data.datasets[0].data.push(Math.ceil(item.count/total*100));
        }
      });
      this.pieChart.update();
    })

  }

  getRandomInt(max:number){
    return Math.floor(Math.random()*max);
  }

  randomColor(list:any){
    let index = this.getRandomInt(list.length);
    let colorStr = list[index];
    list.splice(index,1);
    return colorStr;
  }
  
  pieChart:any;
  canvas : any;
  ctx : any;

  barChart:any;
  canvas1:any;
  ctx1:any;

  initChart(){
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');

    this.pieChart = new Chart(this.ctx, {
      type: 'pie',
      data: {
          labels: [],
          datasets: [{
              label: '# of Votes',
              data: [],
              backgroundColor: [ ],
              borderWidth: 1,
              hoverOffset: 4
          }]
      },
      options: {
        responsive: true,
      }
    });

    this.canvas1 = document.getElementById('myChart2');
    this.ctx1 = this.canvas1.getContext('2d');
     this.barChart = new Chart(this.ctx1, {
      type: 'bar',
      data: {
          labels: [],
          datasets: [{
              label: 'Doanh Thu Hàng Tháng',
              data: [],
              backgroundColor: [],
              borderWidth: 1
          }]
      },
      options: {
        responsive: false,
        scales: {
          x: {},
          y: {
            min: 10
          }
        },
        plugins: {
          legend: {
            display: true,
          },
          datalabels: {
            anchor: 'end',
            align: 'end'
          }
        }
      }
    });
  }

  // @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  // public barChartOptions: ChartConfiguration['options'] = {
  //   responsive: true,
  //   // We use these empty structures as placeholders for dynamic theming.
  //   scales: {
  //     x: {},
  //     y: {
  //       min: 0,
  //       ticks:{
  //         stepSize: 100000
  //       }
  //     }
  //   },
  //   plugins: {
  //     legend: {
  //       display: true,
  //     },
  //     datalabels: {
  //       anchor: 'end',
  //       align: 'end',
  //     }
  //   },
  //   events: []
  // };

  // public barChartType: ChartType = 'bar';
  // public barChartPlugins = [
  //   DataLabelsPlugin
  // ];

  // public barChartData: ChartData<'bar'> = {
  //   labels: [  ],
  //   datasets: [
  //     { data: [  ], label: 'Doanh số' }
  //   ]
  // };

  splitStringToArray(title : string):any{
    let listWord = title.split(" ");
    let listArray: any[] = [];
    for(let i =0; i<listWord.length; i+=2){
      if(listWord[i+1] == null){
        listArray.push(listWord[i]);
      } else {
        listArray.push(listWord[i] + " " + listWord[i+1]);
      }
    }
    return listArray;
  }


}
