import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import CustomStore from 'devextreme/data/custom_store';
import 'devextreme/data/odata/store';

@Component({
  templateUrl: 'tasks.component.html'
})

export class TasksComponent {
  dataSource: any;
  statuses: any;
  editorOptions: object;

  constructor(private http: HttpClient) {
    function isNotEmpty(value: any): boolean {
                return value !== undefined && value !== null && value !== "";
            }

    this.editorOptions = {
        itemTemplate: "statusTemplate"
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
                  console.log(data.data)
                  return {
                      data: data.data,
                      totalCount: data.totalCount,
                      summary: data.summary,
                      groupCount: data.groupCount
                  };
              })
              .catch(error => { throw 'Data Loading Error' });
      },
    insert: function(values: string) {
        console.log(values)
        console.log(JSON.stringify(values))
        return http.post<any>("http://localhost:8081/tasks/create", JSON.stringify(values), {headers: new HttpHeaders({'Content-Type':  'application/json'}),
        withCredentials: true}).toPromise().then((data: any) => {
            return {
                data: data.data,
                totalCount: data.totalCount,
                summary: data.summary,
                groupCount: data.groupCount
            };
        });
    },
    update: function(key: string, values: object) {
        console.log("UPDATING...")
        console.log(key)
        console.log(values)
        console.log(JSON.stringify(values))
        return http.put<any>("http://localhost:8081/tasks/update", JSON.stringify({id: key, ...values}), {headers: new HttpHeaders({'Content-Type':  'application/json'}),
        withCredentials: true}).toPromise().then((data: any) => {
            return {
                data: data.data,
                totalCount: data.totalCount,
                summary: data.summary,
                groupCount: data.groupCount
            };
        });
    },

    remove: function(key: string) {
        console.log("DELETING...")
        console.log(key)
        return http.request<any>("delete", "http://localhost:8081/tasks/delete", {body: JSON.stringify({id: key}), headers: new HttpHeaders({'Content-Type':  'application/json'}),
        withCredentials: true}).toPromise();
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
        return http.get('http://localhost:8081/tasks/statuses/all', { params: params, withCredentials: true })
            .toPromise()
            .then((data: any) => {
                console.log(data.data)
                return {
                    data: data.data,
                    totalCount: data.totalCount,
                    summary: data.summary,
                    groupCount: data.groupCount
                };
            })
            .catch(error => { throw 'Data Loading Error' });
        
    },
    byKey: function(key: string){
        return http.get('http://localhost:8081/tasks/statuses/'+key, { withCredentials: true })
        .toPromise()
        .then((data: any) => {
            console.log("BY KEY")
            console.log(data)
            return {
                data: key,
            };
        })
        .catch(error => { throw 'Data Loading Error' });
    }
});
  }
}
