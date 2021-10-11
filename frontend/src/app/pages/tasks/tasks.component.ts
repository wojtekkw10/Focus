import { HttpClient, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import CustomStore from 'devextreme/data/custom_store';
import 'devextreme/data/odata/store';

@Component({
  templateUrl: 'tasks.component.html'
})

export class TasksComponent {
  dataSource: any;
  statuses: any;

  constructor(private http: HttpClient) {
    function isNotEmpty(value: any): boolean {
                return value !== undefined && value !== null && value !== "";
            }
    this.dataSource = new CustomStore({
      key: "id",
      load: function (loadOptions: any) {
          let params: HttpParams = new HttpParams();
          [
              "skip",
              "take",
              "requireTotalCount",
              "requireGroupCount",
              "sort",
              "filter",
              "totalSummary",
              "group",
              "groupSummary"
          ].forEach(function(i) {
              if (i in loadOptions && isNotEmpty(loadOptions[i]))
                  params = params.set(i, JSON.stringify(loadOptions[i]));
          });
          return http.get('http://localhost:8081/tasks/all', { params: params, withCredentials: true })
              .toPromise()
              .then((data: any) => {
                  return {
                      data: data.data,
                      totalCount: data.totalCount,
                      summary: data.summary,
                      groupCount: data.groupCount
                  };
              })
              .catch(error => { throw 'Data Loading Error' });
      }
  });

  this.statuses = new CustomStore({
    key: "id",
    load: function (loadOptions: any) {
        let params: HttpParams = new HttpParams();
        [
            "skip",
            "take",
            "requireTotalCount",
            "requireGroupCount",
            "sort",
            "filter",
            "totalSummary",
            "group",
            "groupSummary"
        ].forEach(function(i) {
            if (i in loadOptions && isNotEmpty(loadOptions[i]))
                params = params.set(i, JSON.stringify(loadOptions[i]));
        });
        return http.get('http://localhost:8081/tasks/statuses', { params: params, withCredentials: true })
            .toPromise()
            .then((data: any) => {
                return {
                    data: data.data,
                    totalCount: data.totalCount,
                    summary: data.summary,
                    groupCount: data.groupCount
                };
            })
            .catch(error => { throw 'Data Loading Error' });
    }
});
  }
}
