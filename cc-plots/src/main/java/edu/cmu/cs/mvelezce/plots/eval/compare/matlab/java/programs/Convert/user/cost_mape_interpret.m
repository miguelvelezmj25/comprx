interpret_approaches = ["Comprex"; "LR & R200"];
interpret_cost = [25.1 42.0];
interpret_mape = [10.6 434.5];

not_interpret_approaches = ["RF & R200"];
not_interpret_cost = [40.4];
not_interpret_mape = [5.5];

plot(interpret_cost, interpret_mape, '+', not_interpret_cost, not_interpret_mape, 'x', 'LineWidth', 2 ,'MarkerSize',16);

title('Density Converter', 'Fontsize',80);
xlabel('Cost (m)');
ylabel('Accuracy (MAPE)');

xlim([24.5, 47.0]);
ylim([-10.0, 450.0]);

set(gca,'FontSize',20);

dx = 0.7; dy = 0.6; 
text(interpret_cost+dx, interpret_mape+dy, interpret_approaches, 'Fontsize', 16);
text(not_interpret_cost+dx, not_interpret_mape+dy, not_interpret_approaches, 'Fontsize', 16);

legend('Interpretable','Not interpretable','Location','northwest');

set(gcf,'Position',[100 100 550 400])

saveas(gcf,'Convert.pdf');