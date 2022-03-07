import { Component, OnInit, ViewChild  } from '@angular/core';
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
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
  public totalArtist: any;

  constructor(private dashboardSer: DashboardService) { }

  ngOnInit(): void {
    this.dashboardSer.getDashboard().subscribe((data) => {
      this.listTrending = data.content.listTrending;
      this.barChartData.labels = [];
      this.barChartData.datasets[0].data = [];
      this.listTrending.forEach((song:any) => {

          this.barChartData.labels?.push(this.splitStringToArray(song.title));
          this.barChartData.datasets[0].data.push(song.listenCountReset);
      });
      this.chart?.update();
      this.totalSong = data.content.totalSong;
      this.totalAlbum = data.content.totalAlbum;
      this.totalArtist = data.content.totalArtist;
    })
  }

  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
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
      { data: [  ], label: 'Lượt nghe Trending' }
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
