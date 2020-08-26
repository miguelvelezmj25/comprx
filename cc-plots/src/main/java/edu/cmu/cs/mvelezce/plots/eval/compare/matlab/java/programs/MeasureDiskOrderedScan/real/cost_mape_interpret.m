interpret_approaches = ["Comprex"; "LR & R200"];
interpret_cost = [41.4 41.3];
interpret_mape = [5.0 14.9];

not_interpret_approaches = ["RF & R200"];
not_interpret_cost = [36.4];
not_interpret_mape = [1.1];

plot(interpret_cost, interpret_mape, '+', not_interpret_cost, not_interpret_mape, 'x', 'LineWidth', 2 ,'MarkerSize',16);

title('Berkeley DB', 'Fontsize',80);
xlabel('Cost (m)');
ylabel('Accuracy (MAPE)');

xlim([36.0, 43.0]);
ylim([0.5, 16]);

set(gca,'FontSize',20);

dx = 0.1; dy = 0.4; 
text(interpret_cost+dx, interpret_mape+dy, interpret_approaches, 'Fontsize', 16);
text(not_interpret_cost+dx, not_interpret_mape+dy, not_interpret_approaches, 'Fontsize', 16);

legend('Interpretable','Not interpretable','Location','northwest');

set(gcf,'Position',[100 100 550 400])

saveas(gcf,'MeasureDiskOrderedScan.pdf');