export interface MeasureDto {
  title: string;
  type: MeasureType;
  code: string;
  content: string;
}

export enum MeasureType {
  Main = 'MAIN',
  Standard = 'STANDARD',
  High = 'HIGH'
}
