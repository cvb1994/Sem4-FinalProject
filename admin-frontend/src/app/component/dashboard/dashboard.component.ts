import { Component, OnInit, ViewChild  } from '@angular/core';
import { ChartConfiguration, ChartData, ChartEvent, ChartType, Ticks } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import DataLabelsPlugin from 'chartjs-plugin-datalabels';
import { DashboardService } from 'src/app/service/dashboard.service';

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
  public current = new Date();

  constructor(private dashboardSer: DashboardService) { }

  ngOnInit(): void {
    this.dashboardSer.getDashboard().subscribe((data) => {
      this.listTrending = data.content.listTrending;
      console.log(this.listTrending);
      
      this.totalSong = data.content.totalSong;
      this.totalAlbum = data.content.totalAlbum;
      this.totalUser = data.content.totalUser;
      this.newAlbum = data.content.newAlbumInMonth;
      this.newUser = data.content.newUserInMonth;
      this.newPayment = data.content.newPaymentInMonth;
    });
    this.dashboardSer.getProfitRePort(this.defaultMonthProfit).subscribe((data) =>{
      this.listProfit = data.content;
      this.barChartData.labels = [];
      this.barChartData.datasets[0].data = [];
      this.listProfit.forEach((item:any) => {
          this.barChartData.labels?.push(item.month+"-"+item.year);
          this.barChartData.datasets[0].data.push(item.profit);
      });
      this.chart?.update();
    })
  }

  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: {
      x: {},
      y: {
        min: 0,
        ticks:{
          stepSize: 100000
        }
      }
    },
    plugins: {
      legend: {
        display: true,
      },
      datalabels: {
        anchor: 'end',
        align: 'end',
      }
    },
    events: []
  };

  public barChartType: ChartType = 'bar';
  public barChartPlugins = [
    DataLabelsPlugin
  ];

  public barChartData: ChartData<'bar'> = {
    labels: [  ],
    datasets: [
      { data: [  ], label: 'Doanh số' }
    ]
  };

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
